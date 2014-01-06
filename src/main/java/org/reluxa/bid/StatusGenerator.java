package org.reluxa.bid;

import org.reluxa.vaadin.widget.AbstractColumnGenerator;
import org.reluxa.vaadin.widget.Icon;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

public class StatusGenerator extends AbstractColumnGenerator<Bid,Label> {

	@Override
	public Label generateCell(Bid value) {
		return new Label(getIcon(value.getStatus())+value.getStatus(),ContentMode.HTML);
	}
	
	private Icon getIcon(String statusText) {
//		BidStatus status = BidStatus.valueOf(statusText);
//		if (status == BidStatus.WON) {
//			return Icon.get("smile");
// 		} else if (status == BidStatus.LOST) {
// 			return Icon.get("sad");
// 		} else if (status == BidStatus.PENDING) {
// 			return Icon.get("neutral");
// 		} else if (status == BidStatus.WAITING_FOR_APPOVAL) {
// 			return Icon.get("busy");
// 		}
		return Icon.get("na");
	}

}
