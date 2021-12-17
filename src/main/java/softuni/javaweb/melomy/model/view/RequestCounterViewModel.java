package softuni.javaweb.melomy.model.view;

public class RequestCounterViewModel {

    private final Integer authorizedRequests;
    private final Integer anonymousRequests;
    private final Integer getRequests;
    private final Integer postRequests;
    private final Integer deleteRequests;

    public RequestCounterViewModel(int authorizedRequests, int anonymousRequests, Integer getRequests, Integer postRequests, Integer deleteRequests) {
        this.authorizedRequests = authorizedRequests;
        this.anonymousRequests = anonymousRequests;
        this.getRequests = getRequests;
        this.postRequests = postRequests;
        this.deleteRequests = deleteRequests;
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

    public Integer getGetRequests() {
        return getRequests;
    }

    public Integer getPostRequests() {
        return postRequests;
    }

    public Integer getDeleteRequests() {
        return deleteRequests;
    }
}
