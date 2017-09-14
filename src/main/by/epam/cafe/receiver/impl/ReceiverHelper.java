package by.epam.cafe.receiver.impl;

import by.epam.cafe.constant.SessionAtr;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.entity.Role;
import by.epam.cafe.entity.User;

class ReceiverHelper {
    private static final String ARCHIVE_STATUS_PARAMETER = "archive_status";
    private static final String ARCHIVE_STATUS_MARKER = "archiveStatusMarker";

    Boolean getArchiveStatus(RequestContent content) {
        Boolean archiveStatus = null;

        User user = (User)content.getSessionAttributes().get(SessionAtr.USER);
        if (user.getRole().equals(Role.CLIENT)
                || content.getRequestParameters().get(ARCHIVE_STATUS_PARAMETER) == null) {
            archiveStatus = false;
            content.setRequestAttributes(ARCHIVE_STATUS_MARKER, "false");
        } else {
            archiveStatus = Boolean.valueOf(content.getRequestParameters().get(ARCHIVE_STATUS_PARAMETER)[0]);
            content.setRequestAttributes(ARCHIVE_STATUS_MARKER, archiveStatus.toString());
        }

        return archiveStatus;
    }
}
