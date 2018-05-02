package ai.tact.qa.automation.Helper;

import com.paypal.selion.configuration.Config;
import com.paypal.selion.configuration.Config.ConfigProperty;
import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.platform.html.*;
import com.paypal.selion.platform.html.support.events.*;
import com.paypal.selion.reports.runtime.SeLionReporter;

public class SmartScreenshots extends AbstractElementEventListener {
    private static ThreadLocal<Boolean> interactedWithPage = new ThreadLocal<Boolean>(){
        @Override
        protected Boolean initialValue()
        {
            return false;
        }
    };

    /**
     * If we register this ElementEventListener we should enable AUTO_SCREEN_SHOT functionality from SeLion.
     */
    public SmartScreenshots() {
        Config.setConfigProperty(ConfigProperty.AUTO_SCREEN_SHOT, "true");
    }

    /**
     * This event gets triggered before we perform a click on an element. The following objects trigger this event,
     * {@link Button}, {@link CheckBox}, {@link DatePicker}, {@link Form}, {@link Image}, {@link Label}, {@link Link},
     * {@link RadioButton}, {@link SelectList}, {@link Table}, {@link TextField}
     *
     * We need to verify if there are chances on the page and if the expected object is provided.
     * If these conditions are met we need to take a screenshot.
     *
     * @param target Instance of the element that triggered this event and implements {@link Clickable}
     * @param expected The expected objects that were passed to the click method
     */
    @Override
    public void beforeClick(Clickable target, Object... expected) {
        if(expected != null && expected.length != 0 && interactedWithPage.get()) {
            SeLionReporter.log(Grid.driver().getTitle(), true, true);
        }
    }

    /**
     * This event gets triggered after we perform a click on an element. The following objects trigger this event,
     * {@link Button}, {@link CheckBox}, {@link DatePicker}, {@link Form}, {@link Image}, {@link Label}, {@link Link},
     * {@link RadioButton}, {@link SelectList}, {@link Table}, {@link TextField}
     *
     * After we click a object and there is an expected object we will take a screenshot.
     *
     * @param target Instance of the element that triggered this event and implements {@link Clickable}
     * @param expected The expected objects that were passed to the click method
     */
    @Override
    public void afterClick(Clickable target, Object... expected) {
        if(expected != null && expected.length != 0) {
            reset();
        }
    }

    /**
     * This event gets triggered after we start typing in an element. The following objects trigger this event,
     * {@link TextField}
     *
     * @param target Instance of the element that triggered this event and implements {@link Typeable}
     * @param value The value that was typed in the field
     */
    @Override
    public void afterType(Typeable target, String value) {
        interactedWithPage.set(true);
    }

    /**
     * This event gets triggered before we check an element. The following objects trigger this event,
     * {@link CheckBox}, {@link RadioButton}
     *
     * @param target Instance of the element that triggered this event and implements {@link Checkable}
     * @param expected The expected locator that was passed to the check method
     */
    @Override
    public void afterCheck(Checkable target, String expected) {
        interactedWithPage.set(true);
    }

    /**
     * This event gets triggered after we check an element. The following objects trigger this event,
     * {@link CheckBox}, {@link RadioButton}
     *
     * @param target Instance of the element that triggered this event and implements {@link Checkable}
     */
    @Override
    public void afterCheck(Checkable target) {
        interactedWithPage.set(true);
    }

    /**
     * This event gets triggered after we uncheck an element. The following objects trigger this event,
     * {@link CheckBox}
     *
     * @param target Instance of the element that triggered this event and implements {@link Uncheckable}
     * @param expected The expected locator that was passed to the check method
     */
    @Override
    public void afterUncheck(Uncheckable target, String expected) {
        interactedWithPage.set(true);
    }

    /**
     * This event gets triggered after we uncheck an element. The following objects trigger this event,
     * {@link CheckBox}
     *
     * @param target Instance of the element that triggered this event and implements {@link Uncheckable}
     */
    @Override
    public void afterUncheck(Uncheckable target) {
        interactedWithPage.set(true);
    }

    /**
     * This event gets triggered after we submit an element. The following objects trigger this event,
     * {@link Form}
     *
     * @param target Instance of the element that triggered this event and implements {@link Submitable}
     */
    @Override
    public void afterSubmit(Submitable target) {
        interactedWithPage.set(true);
    }

    /**
     * This event gets triggered after select a option in an element. The following objects trigger this event,
     * {@link SelectList}
     *
     * @param target Instance of the element that triggered this event and implements {@link Selectable}
     * @param index The index of the option that we want to select
     */
    @Override
    public void afterSelect(Selectable target, int index) {
        interactedWithPage.set(true);
    }

    /**
     * This event gets triggered after select a option in an element. The following objects trigger this event,
     * {@link SelectList}
     *
     * @param target Instance of the element that triggered this event and implements {@link Selectable}
     * @param value The value that was selected in the List
     */
    @Override
    public void afterSelect(Selectable target, String value) {
        interactedWithPage.set(true);
    }

    /**
     * This event gets triggered after deselect a option in an element. The following objects trigger this event,
     * {@link SelectList}
     *
     * @param target Instance of the element that triggered this event and implements {@link Selectable}
     * @param index The index of the option that we want to deselect
     */
    @Override
    public void afterDeselect(Deselectable target, int index) {
        interactedWithPage.set(true);
    }

    /**
     * This event gets triggered after deselect a option in an element. The following objects trigger this event,
     * {@link SelectList}
     *
     * @param target Instance of the element that triggered this event and implements {@link Selectable}
     * @param value The value that was deselected in the List
     */
    @Override
    public void afterDeselect(Deselectable target, String value) {
        interactedWithPage.set(true);
    }

    /**
     * This event gets triggered after deselect a option in an element. The following objects trigger this event,
     * {@link SelectList}
     *
     * @param target Instance of the element that triggered this event and implements {@link Selectable}
     */
    @Override
    public void afterDeselect(Deselectable target) {
        interactedWithPage.set(true);
    }

    /**
     * Reset the interaction with page Boolean, should only be used if you want to prevent from a screenshot to be taken.
     */
    public static void reset() {
        interactedWithPage.set(false);
    }

}
