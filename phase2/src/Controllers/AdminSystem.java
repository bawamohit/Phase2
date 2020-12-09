package Controllers;

import UI.AdminPresenter;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class AdminSystem extends UserSystem{
    private AdminPresenter presenter;

    public AdminSystem(){
        this.presenter = new AdminPresenter();
    }

    /**
     * Implements the run method for all admin users.
     * With this method, an admins can logout, message (view, send, receive), view available events,
     * signup and remove themselves from events.
     *
     */
    @Override
    void run(String username, TechConferenceSystem tcs) {
        Scanner scanner = new Scanner(System.in);
        label:
        while (true) {
            presenter.printAdminMenu();
            String choice = scanner.nextLine();
            List<UUID> emptyEvents = tcs.getEM().getEmptyEvents();

            switch (choice){
                case "0":
                    presenter.printLoggedOut();
                    break label;
                case "1": // Delete Chat
                    String username1;
                    String username2;

                    inner:
                    while (true) {
                        presenter.printDeleteChatMenu();
                        while (true) {
                            presenter.printAskUsername1();
                            presenter.printBackToMainMenu();
                            username1 = scanner.nextLine();
                            if (username1.equals("")){
                                break inner;
                            }
                            if (tcs.getUM().isRegistered(username1) == false) {
                                presenter.printUserDoesNotExist();
                            } else {
                                break;
                            }
                        }

                        while (true) {
                            presenter.printAskUsername2();
                            presenter.printBackToMainMenu();
                            username2 = scanner.nextLine();
                            if (username2.equals("")){
                                break inner;
                            }
                            if (username1.equals(username2)){
                                presenter.printDeleteChatError();
                            }
                            else if (tcs.getUM().isRegistered(username1) == false) {
                                presenter.printUserDoesNotExist();
                            } else {
                                break;
                            }
                        }
                        presenter.confirmChatDeletion(username1, username2);
                        String confirmation = scanner.nextLine();

                        if (confirmation == "yes"){
                            tcs.getMM().deleteMutualThread(username1, username2);
                        }
                        break;
                    }
                    break;

                case "2":
                    presenter.printDeleteEventMenu();
                    List<UUID> emptyEventsIds = tcs.getEM().getEmptyEvents();
                    List<String> emptyEventStrings = tcs.getEM().getEventsStrings(emptyEventsIds);
                    presenter.printEmptyEvents(formatInfo(emptyEventStrings));

            }
        }
    }
}
