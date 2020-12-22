package com.sstep.http.controller.interfaces;

import com.sstep.http.Url;
import com.sstep.http.entity.FileContent;


/**
 * @author sstepanov
 */
public interface WebController {

    FileContent getContent(Url url);
}
