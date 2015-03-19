package com.wikia.webdriver.pageobjectsfactory.componentobject.visualeditordialogs;

import com.wikia.webdriver.common.core.Assertion;
import com.wikia.webdriver.common.logging.PageObjectLogging;
import com.wikia.webdriver.pageobjectsfactory.pageobject.visualeditor.VisualEditorPageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * @author Karol 'kkarolk' Kujawiak
 * @author Robert 'rochan' Chan
 */
public class VisualEditorHyperLinkDialog extends VisualEditorDialog {

  @FindBy(css = ".oo-ui-icon-previous")
  private WebElement previousButton;
  @FindBy(css = ".ve-ui-mwLinkTargetInputWidget input")
  private WebElement linkInput;
  @FindBy(css = ".oo-ui-pendingElement-pending")
  private WebElement inputPending;
  @FindBy(css = ".oo-ui-optionWidget-selected")
  private WebElement selectedResult;
  @FindBy(css = ".ve-ui-desktopContext")
  private WebElement desktopContext;
  @FindBy(css = ".oo-ui-processDialog-title.oo-ui-labelElement")
  private WebElement title;
  @FindBy(css = ".oo-ui-window.ve-ui-inspector")
  private WebElement inspector;

  private By linkResultMenuBy = By.cssSelector(".ve-ui-mwLinkTargetInputWidget-menu");
  private By matchingResultBy = By.cssSelector(".oo-ui-optionWidget-selected span");
  private By linkResultsBy = By.cssSelector("li");
  private By linkCategoryBy = By.cssSelector(".oo-ui-labeledElement-label span");
  private By highlightedResultsBy = By.cssSelector(".oo-ui-optionWidget-highlighted");
  private By doneButtonBy = By.cssSelector(".oo-ui-window-foot .oo-ui-buttonElement-button");

  private String menuSectionItemText = "oo-ui-menuSectionItemWidget";

  private int[] pageCategoryIndex = new int[4];
  private static final int NEW_PAGE_INDEX = 0;
  private static final int MATCHING_PAGE_INDEX = 1;
  private static final int EXTERNAL_LINK_INDEX = 2;
  private static final int REDIRECT_PAGE_INDEX = 3;

  public VisualEditorHyperLinkDialog(WebDriver driver) {
    super(driver);
    pageCategoryIndex[NEW_PAGE_INDEX] = -1;
    pageCategoryIndex[MATCHING_PAGE_INDEX] = -1;
    pageCategoryIndex[EXTERNAL_LINK_INDEX] = -1;
    pageCategoryIndex[REDIRECT_PAGE_INDEX] = -1;
  }

  public void clickDoneButton() {
    waitForDialogVisible();
    WebElement doneButton = dialog.findElement(doneButtonBy);
    doneButton.click();
    waitForDialogNotVisible();
  }

  public void typeInLinkInput(String text) {
    waitForDialogVisible();
    waitForElementClickableByElement(linkInput);
    linkInput.sendKeys(text);
    waitForElementNotVisibleByElement(inputPending);
    waitForValueToBePresentInElementsAttributeByElement(linkInput, "value", text);
    //Due to fast typing, refocus on the input to force type ahead suggestion to refresh
    title.click();
  }

  private void viewLinkResults() {
    waitForElementNotVisibleByElement(inputPending);
    waitForElementVisibleByElement(desktopContext);
    List<WebElement> linkResults =
        desktopContext.findElement(linkResultMenuBy).findElements(linkResultsBy);
    for (int i = 0; i < linkResults.size(); i++) {
      WebElement linkResult = linkResults.get(i);
      String elementClassName = linkResult.getAttribute("class");
      if (elementClassName.contains(menuSectionItemText)) {
        String linkCategory = linkResult.findElement(linkCategoryBy).getText();
        switch (linkCategory) {
          case "New page":
            pageCategoryIndex[NEW_PAGE_INDEX] = i;
            break;
          case "Matching page":
            pageCategoryIndex[MATCHING_PAGE_INDEX] = i;
            break;
          case "Matching pages":
            pageCategoryIndex[MATCHING_PAGE_INDEX] = i;
            break;
          case "Redirect page":
            pageCategoryIndex[REDIRECT_PAGE_INDEX] = i;
            break;
          case "External link":
            pageCategoryIndex[EXTERNAL_LINK_INDEX] = i;
            break;
          default:
            throw new NoSuchElementException("Non-existing link category selected");
        }
      }
    }
    PageObjectLogging.log("viewResults", "Category indexes sorted", true);
  }

  private boolean isCategoryResult(int category) {
    viewLinkResults();
    return (pageCategoryIndex[category] == -1);
  }

  public void isNewPage() {
    Assertion.assertTrue(isCategoryResult(NEW_PAGE_INDEX), "New page index not found");
  }

  public void isMatchingPage() {
    Assertion.assertTrue(isCategoryResult(MATCHING_PAGE_INDEX), "Matching page index not found");
  }

  public void isExternalLink() {
    Assertion.assertTrue(isCategoryResult(EXTERNAL_LINK_INDEX), "External link index not found");
  }

  public void isRedirectPage() {
    Assertion.assertTrue(isCategoryResult(REDIRECT_PAGE_INDEX), "Redirect page index not found");
  }

  public void verifyNewPageIsTop() {
    viewLinkResults();
    Assertion.assertNumber(0, pageCategoryIndex[NEW_PAGE_INDEX],
                           "Checking New Page is on the top of the results.");
  }

  public void verifyMatchingPageIsTop() {
    viewLinkResults();
    Assertion.assertNumber(0, pageCategoryIndex[MATCHING_PAGE_INDEX],
                           "Checking Matching Page is on the top of the results.");
  }

  public void verifyExternalLinkIsTop() {
    viewLinkResults();
    Assertion.assertNumber(0, pageCategoryIndex[EXTERNAL_LINK_INDEX],
                           "Checking External Link is on the top of the results.");
  }

  public void verifyRedirectPageIsTop() {
    viewLinkResults();
    Assertion.assertNumber(0, pageCategoryIndex[REDIRECT_PAGE_INDEX],
                           "Checking Redirect Page is on the top of the results.");
  }

  public VisualEditorPageObject clickLinkResult() {
    waitForElementNotVisibleByElement(inputPending);
    waitForElementByElement(selectedResult);
    WebElement matchingResult = desktopContext.findElement(matchingResultBy);
    waitForElementClickableByElement(matchingResult);
    matchingResult.click();
    switchToIFrame();
    waitForElementClickableByElement(previousButton);
    previousButton.click();
    switchOutOfIFrame();
    waitForElementNotVisibleByElement(inspector);
    return new VisualEditorPageObject(driver);
  }
}
