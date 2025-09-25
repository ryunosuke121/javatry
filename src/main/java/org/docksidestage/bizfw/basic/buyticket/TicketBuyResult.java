package org.docksidestage.bizfw.basic.buyticket;

// TODO ito javadocお願いします by jflute (2025/09/25)
public class TicketBuyResult {
    private final Ticket ticket;
    private final int change;

    public TicketBuyResult(Ticket ticket, int change) {
        this.ticket = ticket;
        this.change = change;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public int getChange() {
        return change;
    }
}
