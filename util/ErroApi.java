package util;

public final class ErroApi extends RuntimeException {
    private final int status;

    public ErroApi(int status, String mensagem) {
        super(mensagem);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
