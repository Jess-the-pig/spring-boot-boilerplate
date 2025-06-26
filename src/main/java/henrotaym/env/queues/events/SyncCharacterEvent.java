package henrotaym.env.queues.events;

public class SyncCharacterEvent implements Event {

    public String EVENT_NAME;
    private String page;

    public SyncCharacterEvent(String EVENT_NAME, String page) {
        this.EVENT_NAME = EVENT_NAME;
        this.page = page;
    }

    public SyncCharacterEvent() {
        // Constructeur par défaut pour la désérialisation JSON
    }

    @Override
    public String eventName() {
        return EVENT_NAME;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
