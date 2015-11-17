package com.wikia.webdriver.testcases.adstests;

import com.wikia.webdriver.common.templates.TemplateNoFirstLoad;
import com.wikia.webdriver.pageobjectsfactory.pageobject.adsbase.AdsTaboolaObject;

import org.testng.annotations.Test;

/**
 * @ownership AdEngineering Wikia
 */
public class TestAdsTaboolaOasis extends TemplateNoFirstLoad {

  @Test(groups = "AdsTaboolaOasis")
  public void adsTaboolaOasis() {

    String testedPage = urlBuilder.getUrlForPath("adtest", "SyntheticTests/Taboola");
    testedPage = urlBuilder.appendQueryStringToURL(testedPage, AdsTaboolaObject.URL_PARAM_TRIGGER);

    AdsTaboolaObject adsTaboolaObject = new AdsTaboolaObject(driver);
    adsTaboolaObject.getUrl(testedPage);

    adsTaboolaObject.verifyTaboolaContainer(AdsTaboolaObject.RIGHT_RAIL_CSS_SELECTOR);
    adsTaboolaObject.verifyTaboolaContainer(AdsTaboolaObject.BELOW_ARTICLE_CSS_SELECTOR);

    adsTaboolaObject.verifyTaboolaAdsPresent(AdsTaboolaObject.RIGHT_RAIL_CSS_SELECTOR);
    adsTaboolaObject.verifyTaboolaAdsPresent(AdsTaboolaObject.BELOW_ARTICLE_CSS_SELECTOR);

  }

}
