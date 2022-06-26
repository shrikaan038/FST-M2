package Activity2;

public class NotEnoughFundsException extends RuntimeException{
    public NotEnoughFundsException(Integer amount, Integer balance) {
        super(String.format("Attempted to withdraw %d with a balance of %d", amount, balance));
    }
}
