package jp.co.goalist.hrog.hrog.user.model;

/**
 * Created by RicoShiota on 2015/12/23.
 */
public class UserModel {
    private boolean notifyAtMorning;
    private boolean notifyAtNoon;
    private boolean notifyAtEvening;
    private boolean notifyForNonPeriodic;


    public UserModel() {

    }


    public UserModel(boolean notifyAtMorning, boolean notifyAtNoon, boolean notifyAtEvening, boolean notifyForNonPeriodic) {
        this.notifyAtMorning = notifyAtMorning;
        this.notifyAtNoon = notifyAtNoon;
        this.notifyAtEvening = notifyAtEvening;
        this.notifyForNonPeriodic = notifyForNonPeriodic;
    }

    public boolean isNotifyAtMorning() {
        return notifyAtMorning;
    }

    public void setNotifyAtMorning(boolean notifyAtMorning) {
        this.notifyAtMorning = notifyAtMorning;
    }

    public boolean isNotifyAtNoon() {
        return notifyAtNoon;
    }

    public void setNotifyAtNoon(boolean notifyAtNoon) {
        this.notifyAtNoon = notifyAtNoon;
    }

    public boolean isNotifyAtEvening() {
        return notifyAtEvening;
    }

    public void setNotifyAtEvening(boolean notifyAtEvening) {
        this.notifyAtEvening = notifyAtEvening;
    }

    public boolean isNotifyForNonPeriodic() {
        return notifyForNonPeriodic;
    }

    public void setNotifyForNonPeriodic(boolean notifyForNonPeriodic) {
        this.notifyForNonPeriodic = notifyForNonPeriodic;
    }
}
