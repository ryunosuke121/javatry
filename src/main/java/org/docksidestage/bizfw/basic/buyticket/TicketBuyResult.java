package org.docksidestage.bizfw.basic.buyticket;

// TODO done ito javadocお願いします by jflute (2025/09/25)

/**
 * @author ryunosuke.ito
 */
public class TicketBuyResult {
    private final Ticket ticket;
    private final int change;

    /**
     * @param ticket 購入したチケット
     * @param change お釣り
     */
    public TicketBuyResult(Ticket ticket, int change) {
        this.ticket = ticket;
        this.change = change;
    }

    /**
     * @return 購入したチケット
     */
    public Ticket getTicket() {
        return ticket;
    }

    /**
     * @return お釣り
     */
    public int getChange() {
        return change;
    }
}
