package blog.ricardocampos.exception;

public class GeneralException extends RuntimeException {
    private static final long serialVersionUID = -4760399380804101986L;
    private String mensagem;

    public GeneralException(String mensagem) {
        this(mensagem, mensagem);
    }

    public GeneralException(String mensagem, String mensagemSuporte) {
        this(mensagem, mensagemSuporte, null);
    }

    public GeneralException(String mensagem, String mensagemSuporte, Throwable cause) {
        super((mensagem == null || mensagem.isEmpty() || mensagem.equals(mensagemSuporte)) ? mensagemSuporte
                : mensagem + "\nMensagem para o suporte: " + mensagemSuporte, cause);
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public GeneralException(String mensagem, Throwable cause) {
        this(mensagem, mensagem, cause);
    }

}
