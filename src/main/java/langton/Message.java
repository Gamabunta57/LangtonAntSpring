package langton;

public class Message {
    private boolean isSuccess;
    private String message;

    public Message(){
        this(false,"undefined");
    }

    public Message(boolean isSuccess, String message){
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public boolean getIsSuccess(){
        return isSuccess;
    }

    public String getMessage(){
        return message;
    }
}
