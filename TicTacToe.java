import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {
    int boardWidth = 600;
    int boardHeight = 650; // 50px for the text panel on top

    JFrame frame = new JFrame("Tic-Tac-Toe");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel buttonPanel = new JPanel(); // Panel for buttons

    JButton[][] board = new JButton[3][3];
    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;

    boolean gameOver = false;
    int turns = 0;
    boolean singlePlayer = true; // Flag for single player mode

    TicTacToe() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                tile.setBackground(Color.darkGray);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);
                // tile.setText(currentPlayer);

                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (gameOver) return;
                        JButton tile = (JButton) e.getSource();
                        if (tile.getText().isEmpty()) {
                            tile.setText(currentPlayer);
                            turns++;
                            checkWinner();
                            if (!gameOver) {
                                if (singlePlayer && currentPlayer.equals(playerX)) {
                                    makeComputerMove(); // Make computer move if in single-player mode
                                } else {
                                    currentPlayer = (currentPlayer.equals(playerX)) ? playerO : playerX;
                                    textLabel.setText(currentPlayer + "'s turn.");
                                }
                            }
                        }
                    }
                });
            }
        }

        // Button to play again
        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
        buttonPanel.add(playAgainButton);

        // Button to end the game
        JButton endGameButton = new JButton("End Game");
        endGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        buttonPanel.add(endGameButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);
    }

    void checkWinner() {
        // horizontal
        for (int r = 0; r < 3; r++) {
            if (!board[r][0].getText().isEmpty() &&
                board[r][0].getText().equals(board[r][1].getText()) &&
                board[r][1].getText().equals(board[r][2].getText())) {
                setWinner(board[r][0], board[r][1], board[r][2]);
                return;
            }
        }

        // vertical
        for (int c = 0; c < 3; c++) {
            if (!board[0][c].getText().isEmpty() &&
                board[0][c].getText().equals(board[1][c].getText()) &&
                board[1][c].getText().equals(board[2][c].getText())) {
                setWinner(board[0][c], board[1][c], board[2][c]);
                return;
            }
        }

        // diagonal
        if (!board[0][0].getText().isEmpty() &&
            board[0][0].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][2].getText())) {
            setWinner(board[0][0], board[1][1], board[2][2]);
            return;
        }

        // anti-diagonal
        if (!board[0][2].getText().isEmpty() &&
            board[0][2].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][0].getText())) {
            setWinner(board[0][2], board[1][1], board[2][0]);
            return;
        }

        if (turns == 9) {
            setTie();
            return;
        }
    }

    void setWinner(JButton... tiles) {
        for (JButton tile : tiles) {
            tile.setForeground(Color.green);
            tile.setBackground(Color.gray);
        }
        textLabel.setText(currentPlayer + " is the winner!");
        gameOver = true;
    }

    void setTie() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setBackground(Color.gray);
            }
        }
        textLabel.setText("Tie!");
        gameOver = true;
    }

    void makeComputerMove() {
        int row, col;
        do {
            row = (int) (Math.random() * 3);
            col = (int) (Math.random() * 3);
        } while (!board[row][col].getText().isEmpty());

        board[row][col].setText(playerO);
        turns++;
        checkWinner();
        currentPlayer = playerX; // Switch back to player X
        textLabel.setText(currentPlayer + "'s turn.");
    }

    void resetGame() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setText(""); // Reset all buttons
                board[r][c].setEnabled(true); // Re-enable buttons
                board[r][c].setForeground(Color.white); // Reset color
                board[r][c].setBackground(Color.darkGray); // Reset color
            }
        }
        currentPlayer = playerX;
        textLabel.setText("Tic-Tac-Toe");
        gameOver = false;
        turns = 0;
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}
