package softuni.javaweb.melomy.model.view;

public class RequestCounterViewModel {

    private final Integer authorizedRequests;
    private final Integer anonymousRequests;

    public RequestCounterViewModel(int authorizedRequests, int anonymousRequests) {
        this.authorizedRequests = authorizedRequests;
        this.anonymousRequests = anonymousRequests;
    }

    public Integer getTotalRequests(){
        return authorizedRequests + anonymousRequests;
    }

    public Integer getAuthorizedRequests() {
        return authorizedRequests;
    }

    public Integer getAnonymousRequests() {
        return anonymousRequests;
    }
}
