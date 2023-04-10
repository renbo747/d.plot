package com.dplot.common.service;

import com.dplot.common.SOMap;
import com.dplot.common.Status;

public interface InstagramFeedService {
    SOMap selectInstagramFeed(SOMap param) throws Exception;
    SOMap selectMainFeed(SOMap param) throws Exception;
    SOMap insertMainFeed(SOMap param) throws Exception;
}
