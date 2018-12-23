/*!
 * blog.html 页面脚本.
 * 
 * @since: 1.0.0 2017-03-26
 * @author  <https://xiaofei.com>
 */
"use strict";
//# sourceURL=blog.js
    $(".navbar-nav li").click(function (){
        $(".navbar-nav .active").removeClass("active");
        // $(".navbar-nav li").each(function(){
        //     $(this).removeClass("active");
        // });
        $(this).addClass("active");
    })



