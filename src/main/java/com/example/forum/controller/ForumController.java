package com.example.forum.controller;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.controller.form.ReportForm;
import com.example.forum.service.ReportService;
import com.example.forum.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ForumController {
    @Autowired
    ReportService reportService;
    CommentService commentService;

    /*
     * 投稿内容表示処理
     */
    @GetMapping
    public ModelAndView top() {
        ModelAndView mav = new ModelAndView();
        // 投稿を全件取得
        List<ReportForm> contentData = reportService.findAllReport();
        // 画面遷移先を指定
        mav.setViewName("/top");
        // 投稿データオブジェクトを保管
        mav.addObject("contents", contentData);
        return mav;
    }

    /*
     * 新規投稿画面表示
     */
    @GetMapping("/new")
    public ModelAndView newContent() {
        ModelAndView mav = new ModelAndView();
        // form用の空のentityを準備
        ReportForm reportForm = new ReportForm();
        // 画面遷移先を指定
        mav.setViewName("/new");
        // 準備した空のFormを保管
        mav.addObject("formModel", reportForm);
        return mav;
    }

    /*
     * 新規投稿処理
     */
    @PostMapping("/add")
    public ModelAndView addContent(@ModelAttribute("formModel") ReportForm reportForm){
        // 投稿をテーブルに格納
        reportService.saveReport(reportForm);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    //コメント機能
    @PostMapping(value = "/comment/{id}")
    public  ModelAndView comment(@PathVariable Integer id, @ModelAttribute("formModel") CommentForm commentForm) {
        //idをcommentにセットする
        commentForm.setMessageId(id);
        commentService.saveComment(commentForm);
        return new ModelAndView("redirect:/");
    }

    //deleteMapping
    @DeleteMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable Integer id) {
        reportService.delete(id);
        //redirect
        return new ModelAndView("redirect:/");
    }

    //編集画面への遷移
    //GetMapping
    //Formにはidで参照した投稿レコードが入る
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView();
        // form用のentity
        //idをServiceに渡して参照
        ReportForm reportForm = reportService.edit(id);
        // 編集する投稿をFormをセット
        mav.addObject("formModel", reportForm);
        // 画面遷移先を指定
        mav.setViewName("/edit");
        return mav;
    }

    //投稿の更新
    //PostMapping
    @PutMapping("/update/{id}")
    public ModelAndView update(@PathVariable Integer id, @ModelAttribute("formModel") ReportForm reportForm) {
        //更新したい投稿のidをreportFormにセットする
        reportForm.setId(id);
        //saveReportメソッドで更新する
        reportService.saveReport(reportForm);
        return new ModelAndView("redirect:/");
    }
}
