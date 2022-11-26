package poc.spring.resilience4j.domain.exception;

public class CategoriaNotFoundException extends Exception{
    public CategoriaNotFoundException(String msg){
        super(msg);
    }
}
