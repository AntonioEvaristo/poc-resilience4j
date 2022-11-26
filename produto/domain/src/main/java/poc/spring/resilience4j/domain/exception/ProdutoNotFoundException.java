package poc.spring.resilience4j.domain.exception;

public class ProdutoNotFoundException extends Exception{
    public ProdutoNotFoundException(String msg){
        super(msg);
    }
}
