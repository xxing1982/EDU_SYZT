package com.syzton.sunread.controller.book;

import com.syzton.sunread.config.TestContext;
import com.syzton.sunread.config.WebAppContext;
import com.syzton.sunread.service.book.ReviewService;
import junit.framework.TestCase;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, WebAppContext.class})
@WebAppConfiguration
public class ReviewControllerTest extends TestCase {


    @Autowired
    private ReviewService reviewServiceMock;

//    @Autowired
//    private WebAppConfiguration
}