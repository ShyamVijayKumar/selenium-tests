package com.wikia.webdriver.testcases.desktop.adstests;

import com.wikia.webdriver.common.core.annotations.InBrowser;
import com.wikia.webdriver.common.core.drivers.Browser;
import com.wikia.webdriver.common.core.helpers.Emulator;
import com.wikia.webdriver.common.core.url.UrlBuilder;
import com.wikia.webdriver.common.dataprovider.mobile.MobileAdsDataProvider;
import com.wikia.webdriver.common.templates.mobile.MobileTestTemplate;
import com.wikia.webdriver.pageobjectsfactory.pageobject.adsbase.AdsBaseObject;

import org.apache.commons.lang.StringUtils;
import org.testng.annotations.Test;

import java.util.List;

public class TestAdsDfpParamsPresentMercury extends MobileTestTemplate {

  private static final String LINE_ITEM_ID = "282067812";
  private static final String CREATIVE_ID = "50006703732";
  private static final String SRC_MOBILE = "mobile";

  @InBrowser(browser = Browser.CHROME, emulator = Emulator.GOOGLE_NEXUS_5)
  @Test(dataProviderClass = MobileAdsDataProvider.class, dataProvider = "dfpParamsSynthetic", groups = {
      "MobileAds", "AdsDfpParamsPresentSyntheticMercury"})
  public void dfpParamsPresentSyntheticMercury(
      String wikiName,
      String article,
      String queryString,
      String adUnit,
      String slot,
      List<String> pageParams,
      List<String> slotParams
  ) {
    UrlBuilder urlBuilder = UrlBuilder.createUrlBuilderForWiki(wikiName);
    String testedPage = urlBuilder.getUrlForPath(article);
    if (StringUtils.isNotEmpty(queryString)) {
      testedPage = urlBuilder.appendQueryStringToURL(testedPage, queryString);
    }

    AdsBaseObject ads = new AdsBaseObject(testedPage);
    ads.verifyGptIframe(adUnit, slot, SRC_MOBILE);
    ads.verifyGptParams(slot, pageParams, slotParams);
    ads.verifyGptAdInSlot(slot, LINE_ITEM_ID, CREATIVE_ID);
  }

  @InBrowser(browser = Browser.CHROME, emulator = Emulator.GOOGLE_NEXUS_5)
  @Test(dataProviderClass = MobileAdsDataProvider.class, dataProvider = "dfpParams", groups = {
      "MobileAds", "AdsDfpParamsPresentMercury"})
  public void dfpParamsPresentMercury(
      String wikiName,
      String article,
      String adUnit,
      String slot,
      List<String> pageParams,
      List<String> slotParams
  ) {
    String testedPage = UrlBuilder.createUrlBuilderForWiki(wikiName).getUrlForPath(article);
    AdsBaseObject ads = new AdsBaseObject(testedPage);

    ads.verifyGptIframe(adUnit, slot, SRC_MOBILE);
    ads.verifyGptParams(slot, pageParams, slotParams);
  }

}
