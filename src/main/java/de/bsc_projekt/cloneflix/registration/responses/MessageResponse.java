package de.bsc_projekt.cloneflix.registration.responses;

/**
 * Simple model for sending a text message as a request response.
 */
public class MessageResponse {
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
