package henrotaym.env.queues.events;

public class SyncEpisodeEvent implements Event {

    public String EVENT_NAME;
    private int page;

    public SyncEpisodeEvent(String EVENT_NAME, int page) {
        this.EVENT_NAME = EVENT_NAME;
        this.page = page;
    }

    public SyncEpisodeEvent() {
        // Constructeur par défaut pour la désérialisation JSON
    }

    @Override
    public String eventName() {
        return EVENT_NAME;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
