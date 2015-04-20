package com.syzton.sunread.controller.store;

import com.syzton.sunread.controller.BaseController;
import com.syzton.sunread.dto.common.PageResource;
import com.syzton.sunread.model.store.ExchangeHistory;
import com.syzton.sunread.model.store.Gift;
import com.syzton.sunread.model.store.GiftStatus;
import com.syzton.sunread.service.store.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by jerry on 4/20/15.
 */
@Controller
@RequestMapping("/api")
public class StoreController extends BaseController{

    private StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @RequestMapping(value = "/gifts", method = RequestMethod.POST)
    @ResponseBody
    public void addGift(@Valid @RequestBody Gift gift) {

        storeService.addGift(gift);
    }

    @RequestMapping(value = "/gifts/{giftId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteGift(@PathVariable("giftId") long giftId) {

        storeService.deleteGift(giftId);
    }

    @RequestMapping(value = "/students/{studentId}/gifts/{giftId}", method = RequestMethod.PUT)
    @ResponseBody
    public void exchangeGift(@PathVariable("studentId") long studentId,@PathVariable("giftId") long giftId,@Param("count") int count) {

        storeService.exchangeGift(studentId, giftId, count);
    }

    @RequestMapping(value = "/users/{userId}/exchanges/{exchangeId}", method = RequestMethod.PUT)
    @ResponseBody
    public void changeHistoryStatus(@PathVariable("exchangeId") long exchangeId,@PathVariable("userId") long userId) {

        storeService.changeExchangeStatus(exchangeId,userId, GiftStatus.DELIVERED);
    }
    @RequestMapping(value = "/gifts", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<Gift> getGifts(@RequestParam("page") int page,
                         @RequestParam("size") int size) {
        Pageable pageable = this.getPageable(page,size);

        Page<Gift> giftPage = storeService.getGifts(pageable);

        return new PageResource<>(giftPage,"page","size");

    }

    @RequestMapping(value = "/students/{studentId}/exchanges", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<ExchangeHistory> getExchanges(@PathVariable("studentId") long studentId,
                                       @RequestParam("page") int page,
                                       @RequestParam("size") int size) {

        Pageable pageable = this.getPageable(page,size);

        Page<ExchangeHistory> exchangeHistoryPage = storeService.getExchangeHistory(pageable,studentId);

        return new PageResource<>(exchangeHistoryPage,"page","size");

    }
}
