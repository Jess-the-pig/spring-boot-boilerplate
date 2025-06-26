package henrotaym.env.queues.events;

import henrotaym.env.enums.EventName;

public class SyncCharacterEvent implements Event {

    public static final String EVENT_NAME = EventName.SYNC_CHARACTER;
    private String page;

    public SyncCharacterEvent(String page) {
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
