package henrotaym.env.queues.events;

import henrotaym.env.enums.EventName;

public class SyncCharacterEvent implements Event {

    public static final String EVENT_NAME = EventName.SYNC_CHARACTER;
    private Integer page;

    public SyncCharacterEvent(Integer page) {
        this.page = page;
    }

    public SyncCharacterEvent() {}

    @Override
    public String eventName() {
        return EVENT_NAME;
    }

    public Integer getPage() {
        return page;
    }
}
