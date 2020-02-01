function alphanum(a, b) {
 function chunkify(t) {
   var tz = [],
       x = 0,
       y = -1,
       n = 0,
       i,
       j;

   while (i = (j = t.charAt(x++)).charCodeAt(0)) {
     var m = (i === 46 || (i >= 48 && i <= 57));
     if (m !== n) {
       tz[++y] = "";
       n = m;
     }
     tz[y] += j;
   }
   return tz;
 }

 function stringfy(v) {
   if (typeof(v) === "number") {
     v = "" + v;
   }
   if (!v) {
     v = "";
   }
   return v;
 }

 var aa = chunkify(stringfy(a));
 var bb = chunkify(stringfy(b));

 for (x = 0; aa[x] && bb[x]; x++) {
   if (aa[x] !== bb[x]) {
     var c = Number(aa[x]),
         d = Number(bb[x]);

     if (c == aa[x] && d == bb[x]) {
       return c - d;
     } else {
         return (aa[x] > bb[x]) ? 1 : -1;
     }
   }
 }
 return aa.length - bb.length;
}

function numericOnly(a, b) {
 function stripNonNumber(s) {
   s = s.replace(new RegExp(/[^0-9]/g), "");
   return parseInt(s, 10);
 }
 return stripNonNumber(a) - stripNonNumber(b);
}

function numericOnlyWithNegatives(a, b) {
 function stripNonNumber(s) {
   s = s.replace(new RegExp(/[^0-9\-]/g), "");
   return parseInt(s, 10);
 }
 return stripNonNumber(a) - stripNonNumber(b);
}

function datetime(a, b) {
 return Date.parse(a) - Date.parse(b);
}

function winPercentage(a, b) {
  function calculateWinPercentage(a) {
    var stats = a.split('-');
    var wins = parseInt(stats[0]);
    var losses = parseInt(stats[1]);
    var ties = parseInt(stats[2]);
    return ((wins + (ties / 2)) / (wins + losses + ties)) * 100;
  }

  return calculateWinPercentage(a) - calculateWinPercentage(b);
}

function linkText(a, b) {
 function htmlToElement(html) {
   var template = document.createElement('template');
   html = html.trim();
   template.innerHTML = html;
   return template.content.firstChild;
 }

 return alphanum(htmlToElement(a).innerText, htmlToElement(b).innerText);
}
