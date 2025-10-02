package model;

public enum Alphabet {
    // Caractères spéciaux en premier
    POINT(0, "."),
    VIRGULE(1, ","),
    POINT_VIRGULE(2, ";"),
    DEUX_POINTS(3, ":"),
    EXCLAMATION(4, "!"),
    INTERROGATION(5, "?"),
    ESPACE(6, " "),
    APOSTROPHE(7, "'"),
    TIRET(8, "-"),
    PARENTHESE_OUVRANTE(9, "("),
    PARENTHESE_FERMANTE(10, ")"),
    A_MAJ(11, "A"),
    B_MAJ(12, "B"),
    C_MAJ(13, "C"),
    D_MAJ(14, "D"),
    E_MAJ(15, "E"),
    F_MAJ(16, "F"),
    G_MAJ(17, "G"),
    H_MAJ(18, "H"),
    I_MAJ(19, "I"),
    J_MAJ(20, "J"),
    K_MAJ(21, "K"),
    L_MAJ(22, "L"),
    M_MAJ(23, "M"),
    N_MAJ(24, "N"),
    O_MAJ(25, "O"),
    P_MAJ(26, "P"),
    Q_MAJ(27, "Q"),
    R_MAJ(28, "R"),
    S_MAJ(29, "S"),
    T_MAJ(30, "T"),
    U_MAJ(31, "U"),
    V_MAJ(32, "V"),
    W_MAJ(33, "W"),
    X_MAJ(34, "X"),
    Y_MAJ(35, "Y"),
    Z_MAJ(36, "Z"),
    A_MIN(37, "a"),
    B_MIN(38, "b"),
    C_MIN(39, "c"),
    D_MIN(40, "d"),
    E_MIN(41, "e"),
    F_MIN(42, "f"),
    G_MIN(43, "g"),
    H_MIN(44, "h"),
    I_MIN(45, "i"),
    J_MIN(46, "j"),
    K_MIN(47, "k"),
    L_MIN(48, "l"),
    M_MIN(49, "m"),
    N_MIN(50, "n"),
    O_MIN(51, "o"),
    P_MIN(52, "p"),
    Q_MIN(53, "q"),
    R_MIN(54, "r"),
    S_MIN(55, "s"),
    T_MIN(56, "t"),
    U_MIN(57, "u"),
    V_MIN(58, "v"),
    W_MIN(59, "w"),
    X_MIN(60, "x"),
    Y_MIN(61, "y"),
    Z_MIN(62, "z");

    private final int value;
    private final String symbol;

    Alphabet(int value, String symbol) {
        this.value = value;
        this.symbol = symbol;
    }

    public int getValue() {
        return value;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getLongueurAlphabet() {
        return Alphabet.values().length;
    }
}
