package sample.component;

import javafx.scene.Node;
import javafx.scene.control.TreeCell;

public class DirectoryTreeCell extends TreeCell<DirectoryTreeValue> {
    @Override
    protected void updateItem(DirectoryTreeValue item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(item.getText());

            Node icon = item.getIcon();

            /*if (item.getExpandIcon() != null) {
                icon = item.getExpandIcon();
            }*/

            if (item.getExpandIcon() != null && getChildren().size() > 0) {
                if (getTreeItem().isExpanded()) {
                    icon = item.getExpandIcon();
                }
            }

            setGraphic(icon);
        }
    }
}
