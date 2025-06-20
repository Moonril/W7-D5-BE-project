package it.epicode.W7_D5_BE_project.exceptions;

//questa Runtime perchè...nei filter stiamo facendo override di un'altra classe astratta, non possiamo modificare la firma del metodo
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
