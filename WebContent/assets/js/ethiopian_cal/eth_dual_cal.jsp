<%@page contentType="text/javascript" pageEncoding="UTF-8"%>
﻿var EthiopianDate = (function () {
    function EthiopianDate()
    {

    }
    EthiopianDate.c1 = 0x23ab1;
    EthiopianDate.c2 = 0x8eac;
    EthiopianDate.c3 = 0x5b5;
    EthiopianDate.dd = 0x97e;
    EthiopianDate.prototype.getEthMonthName=function(m)
        {
            switch (m)
            {
                case 0:
                    return "መስከረም";

                case 1:
                    return "ጥቅምት";

                case 2:
                    return "ህዳር";

                case 3:
                    return "ታህሳስ";

                case 4:
                    return "ጥር";

                case 5:
                    return "የካቲት";

                case 6:
                    return "መጋቢት";

                case 7:
                    return "ሚያዚያ";

                case 8:
                    return "ግንቦት";

                case 9:
                    return "ሰኔ";

                case 10:
                    return "ሐምሌ";

                case 11:
                    return "ነሀሴ";

                case 12:
                    return "ጳጉሜ";
            }
            return "";
        }
    EthiopianDate.prototype.gregMonthLength= function (year,month)
    {
        switch (month)
        {
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
                return 31;

            case 1:
                if (this.gregIsLeapYear(year))
                    return 29;
                return 28;
        }
        return 30;
    }
    EthiopianDate.prototype.gregIsLeapYear= function (y) {
        return (((y % 4) == 0) && (((y % 100) != 0) || ((y % 400) == 0)));
    }
    EthiopianDate.prototype.ethIsLeapYear = function (y) {
        return ((y % 4) == 3);
    }

    EthiopianDate.prototype.addGregMonths = function (m, y) {
        var sum = 0;
        for (i = 0; i < m ; i++) {
            sum += this.gregMonthLength(y, i);
        }
        return sum;
    }

    EthiopianDate.prototype.fromDayNo=function(dn,ethiopian)
    {
        var num=0;
        var num2=0;
        var num3=0;
        var num4=0;
        if (ethiopian)
        {
            num = Math.floor(dn / 0x5b5);
            num2 = dn % 0x5b5;
            num3 = Math.floor(num2 / 0x16d);
            num4 = num2 % 0x16d;
            if (num2 != 0x5b4)
            {
                return {
                    year:(num * 4) + num3,
                    month:Math.floor(num4 / 30),
                    day:(num4 % 30) + 1,
                };
            }
            else
            {
                return {
                    year:((num * 4) + num3) - 1,
                    month:12,
                    day:6};
            }
        }
        else
        {
            num = Math.floor(dn / 0x23ab1);
            num2 = dn % 0x23ab1;
            num3 = Math.floor(num2 / 0x8eac);
            num4 = num2 % 0x8eac;
            var num5 = Math.floor(num4 / 0x5b5);
            var num6 = num4 % 0x5b5;
            var num7 = Math.floor(num6 / 0x16d);
            var n = num6 % 0x16d;
            var year = ((((num * 400) + (num3 * 100)) + (num5 * 4)) + num7) + 1;
            var month = 0;
            var day = 0;

            while (n >= this.gregMonthLength(year, month)) {
                n -= this.gregMonthLength( year,month);
                month++;
            }
            day = n + 1;
            return new Date(year,month,day);
        }
    }
    EthiopianDate.prototype.dayNoGrig=function(grDate)
    {
        var year = grDate.getFullYear();
        var num = Math.floor((year - 1) / 400);
        var num2 = (year - 1) % 400;
        var num3 = Math.floor(num2 / 100);
        var num4 = num2 % 100;
        var num5 = Math.floor(num4 / 4);
        var num6 = num4 % 4;
        var num7 = this.addGregMonths(grDate.getMonth(), year);
        return (((((((num * 0x23ab1) + (num3 * 0x8eac)) + (num5 * 0x5b5)) + (num6 * 0x16d)) + num7) + grDate.getDate()) - 1);
    }
    EthiopianDate.prototype.dateToEthiopian = function (grDate) {
        return  this.fromDayNo(this.dayNoGrig(grDate)- 0x97e, true);
    }
    EthiopianDate.prototype.ethiopianDateString = function (etDate) {
        return etDate.day + "/" + (etDate.month+1) + "/" + etDate.year;
    }
    EthiopianDate.prototype.parseEthiopianDateString= function (str) {

    }
    EthiopianDate.prototype.ethiopianToDate = function (etDate) {
        return this.fromDayNo(this.dayNoEth(etDate) + 0x97e, false);
    }
    EthiopianDate.prototype.dayNoEth = function (etDate)
    {
        var num = Math.floor( etDate.year / 4);
        var num2 = etDate.year % 4;
        return (((((num * 0x5b5) + (num2 * 0x16d)) + ((etDate.month) * 30)) + etDate.day) - 1);

    }
    EthiopianDate.prototype.ethiopianMonthLength = function (year, month) {
        if (month == 12) {
            return (this.ethIsLeapYear(year) ? 6 : 5);
        }
        return 30;
    }
    EthiopianDate.prototype.ethiopianDayOfWeek = function (etDate)
    {
        var dn = this.dayNoEth(etDate)
        return ((dn + 2) % 7);
    }
    return EthiopianDate;
})();
function addDay(d,n)
{
    return new Date(d.getTime()+ (n*24 * 60 * 60 * 1000));
}
function getMonthName(m)
{
    switch(m)
    {
        case 0: return 'JANUARY';
        case 1: return 'FEBRUARY';
        case 2: return 'MARCH';
        case 3: return 'APRIL';
        case 4: return 'MAY';
        case 5: return 'JUNE';
        case 6: return 'JULY';
        case 7: return 'AUGUST';
        case 8: return 'SEPTEMBER';
        case 9: return 'OCTOBER';
        case 10: return 'NOVEMBER';
        case 11: return 'DECEMBER';
    }
}
function formatDatePart(d)
{
    //return d;
    if (d == null)
        return null;
    d = new Date(d.toString());
    return (d.getMonth()+1) + "/" + d.getDate() + "/" + d.getFullYear();
}
var EthiopianDualCalendar = (function () {
    var picker_html;
    function EthiopianDualCalendar(inputID) {
        var _this = this;
        this.inputEl = $("#" + inputID);
        this.inputEl.focus(function (x) {
            EthiopianDualCalendar.focusedItem = _this;
            var d = new Date(_this.inputEl.attr("dateval"));
            if (d == null || isNaN(d))
                d = new Date();
            EthiopianDualCalendar.prototype.populateForDate(d, d);
            var c = $(EthiopianDualCalendar.container);
            c.css(
                {
                    top: (_this.inputEl.offset().top + _this.inputEl.outerHeight())+"px",
                    left: _this.inputEl.offset().left+"px"
                });
            $(EthiopianDualCalendar.popup_container).fadeIn(null, function ()
            {
                EthiopianDualCalendar.container.focus();
            }
            );

        });
    }
    $.ajax({ url: "<%=request.getContextPath()%>/assets/js/ethiopian_cal/et_dual_cal.html" })
        .done(function (data) {
            picker_html = data;
            var el = document.createElement("div");
            el.innerHTML = picker_html;
            el = el.children.item(0);

            document.getElementsByTagName("body").item(0).appendChild(el);
            EthiopianDualCalendar.container = el.children.item(0);
            EthiopianDualCalendar.popup_container = el;
            $(el).focus(function (x)
            {
                $(EthiopianDualCalendar.popup_container).fadeOut();
            }
            );

            EthiopianDualCalendar.et_table = EthiopianDualCalendar.container.children.item(0).children.item(0);
            EthiopianDualCalendar.gr_table = EthiopianDualCalendar.container.children.item(1).children.item(0);
            EthiopianDualCalendar.button_panel = EthiopianDualCalendar.container.children.item(2);

            EthiopianDualCalendar.clear_button = EthiopianDualCalendar.button_panel.children.item(0);
            EthiopianDualCalendar.today_button = EthiopianDualCalendar.button_panel.children.item(1);

            EthiopianDualCalendar.clear_button.onclick = function ()
            {
                EthiopianDualCalendar.focusedItem.clearDate();
                EthiopianDualCalendar.focusedItem.hideCaleandar();
            }
            EthiopianDualCalendar.today_button.onclick = function () {
                EthiopianDualCalendar.focusedItem.setDate(new Date())
                EthiopianDualCalendar.focusedItem.hideCaleandar();
            }
            var years = EthiopianDualCalendar.et_table.children.item(0).children.item(0).children.item(1).children.item(0).children.item(0);
            years.onchange=function()
            {
                var etdate = EthiopianDate.prototype.dateToEthiopian(EthiopianDualCalendar.viewDate);
                EthiopianDualCalendar.prototype.populateForDate(EthiopianDate.prototype.ethiopianToDate({day:etdate.day,month:etdate.month,year:parseInt(this.value)}), EthiopianDualCalendar.markDate);
            }
            years = EthiopianDualCalendar.gr_table.children.item(0).children.item(0).children.item(1).children.item(0).children.item(0);
            years.onchange = function () {
                EthiopianDualCalendar.prototype.populateForDate(new Date(parseInt(this.value), EthiopianDualCalendar.viewDate.getMonth(), EthiopianDualCalendar.viewDate.getDate()), EthiopianDualCalendar.markDate);
            }

            EthiopianDualCalendar.prototype.populateForDate(new Date(), new Date());
        });

    EthiopianDualCalendar.focusedItem = null;
    EthiopianDualCalendar.prototype.clearDate=function()
    {
        this.selectedDate = null;
        this.inputEl.attr("dateval", "");
        this.inputEl.val("");

    }
    EthiopianDualCalendar.prototype.setDate = function (d) {
        if (d == null) {
            this.clearDate();
        }
        else {
            d = new Date(d.toString())
            if (d == null || isNaN(d)) {
                this.clearDate();
            }
            else {
                this.selectedDate = d;
                
                this.inputEl.attr("dateval",formatDatePart(d));
                this.inputEl.val(formatDatePart(d));
                /*this.inputEl.val(EthiopianDate.prototype.ethiopianDateString(EthiopianDate.prototype.dateToEthiopian(d)));*/
            }
        }
    }
    EthiopianDualCalendar.prototype.selectMonthGr = function (d, m)
    {
        EthiopianDualCalendar.prototype.populateForDate(new Date(d), m == null || m == '' ? null : new Date(m));
    }
    EthiopianDualCalendar.prototype.hideCaleandar=function()
    {
        $(EthiopianDualCalendar.popup_container).fadeOut();
    }
    EthiopianDualCalendar.prototype.selectDate=function(d)
    {
        EthiopianDualCalendar.focusedItem.selectedDate = d;
        EthiopianDualCalendar.focusedItem.inputEl.attr("dateVal", formatDatePart(d));
        EthiopianDualCalendar.focusedItem.inputEl.val(formatDatePart(d));
        /*EthiopianDualCalendar.focusedItem.inputEl.val(EthiopianDate.prototype.ethiopianDateString(EthiopianDate.prototype.dateToEthiopian(new Date(d))));*/
        $(EthiopianDualCalendar.popup_container).fadeOut();
        if(EthiopianDualCalendar.focusedItem.onchange)
        {
            EthiopianDualCalendar.focusedItem.onchange();
        }
    }
    EthiopianDualCalendar.prototype.populateForDate=function(d,mark)
    {
        EthiopianDualCalendar.viewDate = d;
        EthiopianDualCalendar.markDate = mark;

        var etDate = EthiopianDate.prototype.dateToEthiopian(d);

        var monthLength = EthiopianDate.prototype.ethiopianMonthLength(etDate.year, etDate.month)
        var dofw = EthiopianDate.prototype.ethiopianDayOfWeek({ day: 1, month: etDate.month, year: etDate.year });
        var day = 1-dofw;
        var etMark;
        if (mark)
        {
            etMark = EthiopianDate.prototype.dateToEthiopian(mark);
        }
        var commandRow = EthiopianDualCalendar.et_table.children.item(0).children.item(0);
        var prevMonthButton = commandRow.children.item(0).children.item(0);
        var monthLabel = commandRow.children.item(0).children.item(1);
        var nextMonthButton = commandRow.children.item(0).children.item(2);
        var years = commandRow.children.item(1).children.item(0).children.item(0);
        while (years.firstChild)
            years.removeChild(years.firstChild);
        for (del = -20; del <= 20; del++)
        {
            var s = document.createElement("OPTION");
            s.innerText = (etDate.year + del).toString();
            s.value = (etDate.year + del).toString();
            years.appendChild(s)
        }
        years.value = etDate.year.toString();

        monthLabel.innerText = EthiopianDate.prototype.getEthMonthName(etDate.month);


        prevMonthButton.setAttribute("onClick", S("EthiopianDualCalendar.prototype.selectMonthGr('{{d}}','{{m}}')").template(
                            {
                                d: addDay(d, -etDate.day),
                                m: mark == null ? '' : mark
                            }).s);
        nextMonthButton.setAttribute("onClick", S("EthiopianDualCalendar.prototype.selectMonthGr('{{d}}','{{m}}')").template(
                            {
                                d: addDay(d, monthLength-etDate.day+1),
                                m: mark == null ? '' : mark
                            }).s);


        for (row = 2; row < 8; row++) {
            var rowElement = EthiopianDualCalendar.et_table.children.item(0).children.item(row);
            for (col = 0; col < 7; col++) {
                var cellElement = rowElement.children.item(col);
                if (day < 1 || day > monthLength) {
                    cellElement.innerText = "";
                    cellElement.setAttribute("class", "etdc_day etdc_empty_day");
                }
                else
                {
                    cellElement.innerHTML = day.toString();
                    if(etMark && (etMark.day==day && etMark.month==etDate.month && etMark.year==etDate.year))
                        cellElement.setAttribute("class", "etdc_day  etdc_marked_day");
                    else
                        cellElement.setAttribute("class", "etdc_day");
                    cellElement.setAttribute("onclick", S("EthiopianDualCalendar.prototype.selectDate('{{d}}')").template({ d: EthiopianDate.prototype.ethiopianToDate({year:etDate.year,month:etDate.month,day:day}) }).s);
                }
                day++;
            }
        }

        //gregorian calendar
        monthLength = EthiopianDate.prototype.gregMonthLength(d.getFullYear(), d.getMonth())
        dofw = new Date(d.getFullYear(), d.getMonth(), 1).getDay();
        day = 1 - dofw;

        commandRow = EthiopianDualCalendar.gr_table.children.item(0).children.item(0);
        prevMonthButton = commandRow.children.item(0).children.item(0);
        monthLabel = commandRow.children.item(0).children.item(1);
        nextMonthButton = commandRow.children.item(0).children.item(2);
        years = commandRow.children.item(1).children.item(0).children.item(0);

        while (years.firstChild)
            years.removeChild(years.firstChild);
        for (del = -20; del <= 20; del++) {
            var s = document.createElement("OPTION");
            s.innerText = (d.getFullYear()+ del).toString();
            s.value = (d.getFullYear() + del).toString();
            years.appendChild(s)
        }
        years.value = d.getFullYear().toString();

        monthLabel.innerText = getMonthName(d.getMonth());


        prevMonthButton.setAttribute("onClick", S("EthiopianDualCalendar.prototype.selectMonthGr('{{d}}','{{m}}')").template(
                            {
                                d: addDay(new Date(d.getFullYear(), d.getMonth(), 1), -1),
                                m: mark == null ? '' : mark
                            }).s);
        nextMonthButton.setAttribute("onClick", S("EthiopianDualCalendar.prototype.selectMonthGr('{{d}}','{{m}}')").template(
                            {
                                d: addDay(new Date(d.getFullYear(),d.getMonth(),monthLength),1),
                                m: mark==null?'':mark
                            }).s);
        for (row = 2; row < 8; row++) {
            var rowElement = EthiopianDualCalendar.gr_table.children.item(0).children.item(row);
            for (col = 0; col < 7; col++) {
                var cellElement = rowElement.children.item(col);
                if (day < 1 || day > monthLength) {
                    cellElement.innerText = "";
                    cellElement.setAttribute("class", "etdc_day etdc_empty_day");
                    cellElement.setAttribute("onclick", "");
                } else {
                    cellElement.innerHTML = day.toString();
                    if (mark && (mark.getDate()== day && mark.getMonth()== d.getMonth() && mark.getFullYear()== d.getFullYear()))
                        cellElement.setAttribute("class", "etdc_day etdc_marked_day");
                    else
                        cellElement.setAttribute("class", "etdc_day");

                    cellElement.setAttribute("onclick", S("EthiopianDualCalendar.prototype.selectDate('{{d}}')").template({d:new Date(d.getFullYear(),d.getMonth(),day)}).s);
                }
                day++;
            }
        }
    }
    return EthiopianDualCalendar;
})();