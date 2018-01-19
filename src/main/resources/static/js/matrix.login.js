/**
 * @author Admin
 * 处理登陆页面的效果及登陆的前端校验
 */
$(document).ready(function () {
//获取各个jQuery对象
    var login = $('#loginform');
    var recover = $('#recoverform');
    var speed = 400;
//添加登陆和找回密码的滑入和消失效果
    $('#to-recover').click(function () {
        $("#loginform").slideUp();
        $("#recoverform").fadeIn();
    });
    $('#to-login').click(function () {
        $("#recoverform").hide();
        $("#loginform").fadeIn();
    });

    //由找回密码页面返回登陆页面？
    $('#to-login').click(function () {

    });

    if ($.browser.msie == true && $.browser.version.slice(0, 3) < 10) {//检查浏览器版本
        $('input[placeholder]').each(function () { //对所有placeholder遍历执行函数

            var input = $(this);//获取当前的placeholder对象
            //如果输入值和默认值一样就将默认值清空
            $(input).val(input.attr('placeholder'));
            $(input).focus(function () {
                if (input.val() == input.attr('placeholder')) {
                    input.val('');
                }
            });
            //当当前对象失去焦点时，如果输入值为空，或者就是原来的默认值，则重新将默认值显示出来
            $(input).blur(function () {
                if (input.val() == '' || input.val() == input.attr('placeholder')) {
                    input.val(input.attr('placeholder'));
                }
            });
        });
    }
});
//失去焦点时校验是否为空
var loginNameRight=false;
var passwordRight =false;
$(function () {
    $("#loginName").blur(checkLoginName);
    $("#password").blur(checkPassword);
});
function checkLoginName() {
    if ($("#loginName").val() == "") {
        $("#errorForLoginName").html("用户名不能为空，请重新输入");
        $("#errorForLoginName").css("display", "block");
        return loginNameRight;
    }
    $("#errorForLoginName").html("用户名可用");
    $("#errorForLoginName").css("display", "block");
    loginNameRight=true;
    return loginNameRight;
}
function checkPassword() {
    if ($("#password").val() == "") {
        $("#errorForPassWord").html("密码不能为空，请重新输入");
        $("#errorForPassWord").css("display", "block");
        return passwordRight;
    }
    $("#errorForPassWord").html("密码可用");
    $("#errorForPassWord").css("display", "block");
    passwordRight=true;
    return passwordRight;
}
//校验与提交
function login() {
    var loginName = $("#loginName").val();
    var password = $("#password").val();
    if(!checkFinally()){
        $("#errorForPassWord").html("数据不能为空！");
        $("#errorForPassWord").css("display", "block");
        return false;
    }

    $("#loginform").submit();
    /*$.ajax({
        type: 'post',
        url: 'login',
        data:{"username":loginName,"password":password},
        dataType:'json',
        cache:false,
        success:function (data) {
            if (0 == data.result) {
                $("#errorForPassWord").html("用户名或密码有误！");
                $("#errorForPassWord").css("display", "block");
                $("#errorForLoginName").css("display", "none");
                return false;
            }
                $("#errorForLoginName").css("display", "none");
                $("#errorForPassWord").css("display", "none");
                window.location.href = "/main";
            }
    });*/
}
function checkFinally() {
    return !!(loginNameRight == true && passwordRight == true);
}