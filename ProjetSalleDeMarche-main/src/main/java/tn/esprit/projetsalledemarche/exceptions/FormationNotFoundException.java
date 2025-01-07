package tn.esprit.projetsalledemarche.exceptions;

public class FormationNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L; // Correction du nom de la constante

    public FormationNotFoundException(String message) {
        super(message);
    }}