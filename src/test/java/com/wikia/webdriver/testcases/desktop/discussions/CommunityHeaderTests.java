package com.wikia.webdriver.testcases.desktop.discussions;

import com.wikia.webdriver.common.core.Assertion;
import com.wikia.webdriver.common.core.annotations.InBrowser;
import com.wikia.webdriver.common.core.helpers.Emulator;
import com.wikia.webdriver.common.templates.NewTestTemplate;
import com.wikia.webdriver.elements.communities.desktop.components.navigation.local.CommunityHeaderDesktop;
import com.wikia.webdriver.elements.communities.mobile.pages.discussions.PostsListPage;
import com.wikia.webdriver.pageobjectsfactory.pageobject.UserProfilePage;
import com.wikia.webdriver.pageobjectsfactory.pageobject.oasis.MainPage;

import org.testng.annotations.Test;

@InBrowser(emulator = Emulator.DESKTOP_BREAKPOINT_BIG)
public class CommunityHeaderTests extends NewTestTemplate {

  @Test(groups = {"discussions-CommunityHeaderTests"})
  public void wordmarkShouldLinkToMainPage() {
    new PostsListPage().open();
    MainPage mainPage = new CommunityHeaderDesktop().clickWordmark();

    Assertion.assertTrue(mainPage.isMainPage());
  }

  @Test(groups = {"discussions-CommunityHeaderTests"})
  public void wikiNameShouldLinkToMainPage() {
    new PostsListPage().open();
    MainPage mainPage = new CommunityHeaderDesktop().clickWikiName();

    Assertion.assertTrue(mainPage.isMainPage());
  }

  @Test(groups = {"discussions-CommunityHeaderTests"})
  public void avatarShouldLinkToUserPage() {
    new PostsListPage().open();
    CommunityHeaderDesktop ch = new CommunityHeaderDesktop();
    String username = ch.getUserNameFromAvatar(0);
    UserProfilePage userPage = ch.clickUserAvatar(0);

    Assertion.assertEquals(userPage.getUserName(), username);
  }

  @Test(groups = {"discussions-CommunityHeaderTests"})
  public void testExploreMenuLinks() {
    new PostsListPage().open();
    CommunityHeaderDesktop communityHeader = new CommunityHeaderDesktop();

    communityHeader.openExploreMenu().clickExploreWikiActivityLink();

    Assertion.assertTrue(driver.getCurrentUrl().contains("Special:WikiActivity"));

    new PostsListPage().open();

    communityHeader.openExploreMenu().clickExploreCommunityLink();

    Assertion.assertTrue(driver.getCurrentUrl().contains("Special:Community"));

    new PostsListPage().open();

    communityHeader.openExploreMenu().clickExploreVideosLink();

    Assertion.assertTrue(driver.getCurrentUrl().contains("Special:Videos"));

    new PostsListPage().open();

    communityHeader.openExploreMenu().clickExploreImagesLink();

    Assertion.assertTrue(driver.getCurrentUrl().contains("Special:Images"));

    new PostsListPage().open();

    communityHeader.openExploreMenu().clickExploreRandomLink();

    Assertion.assertTrue(driver.getCurrentUrl()
                             .matches(".*\\.wikia\\.com/wiki/(?!Special:Images).*"));
  }
}
