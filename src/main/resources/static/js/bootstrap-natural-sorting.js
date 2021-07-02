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

function alphanumignorecase(a, b) {
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

 var aa = chunkify(stringfy(a.toLowerCase()));
 var bb = chunkify(stringfy(b.toLowerCase()));

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

function sortWithDropdownName(valA, valB, rowA, rowB) {
    var aVal = valA.startsWith('<') ? rowA._data['name'] : valA;
    var bVal = valB.startsWith('<') ? rowB._data['name'] : valB;
    var evalVal = alphanumignorecase(aVal, bVal);
    return evalVal !== 0 && evalEqualValues(rowA, rowB, evalVal);
}

function sortWithDropdownCategory(valA, valB, rowA, rowB) {
    var aVal = valA === undefined ? rowA._data["category-id"] : valA;
    var bVal = valB === undefined ? rowB._data["category-id"] : valB;
    var evalVal = alphanumignorecase(aVal, bVal);
    return evalVal !== 0 && evalEqualValues(rowA, rowB, evalVal);
}

function sortWithDropdownPrice(valA, valB, rowA, rowB) {
    var aVal = valA === undefined ? rowA._data.price : valA;
    var bVal = valB === undefined ? rowB._data.price : valB;
    var evalVal = numericOnlyWithNegatives(aVal, bVal);
    return evalVal !== 0 && evalEqualValues(rowA, rowB, evalVal);
}

function sortWithDropdownAverage(valA, valB, rowA, rowB) {
    var aVal = valA === undefined ? rowA._data.average : valA;
    var bVal = valB === undefined ? rowB._data.average : valB;
    var evalVal = numericOnlyWithNegatives(aVal, bVal);
    return evalVal !== 0 && evalEqualValues(rowA, rowB, evalVal);
}

function sortWithDropdownCount(valA, valB, rowA, rowB) {
    var aVal = valA === undefined ? rowA._data['transaction-count'].toString(10) : valA;
    var bVal = valB === undefined ? rowB._data['transaction-count'].toString(10) : valB;
    var evalVal = numericOnly(aVal, bVal);
    return evalVal !== 0 && evalEqualValues(rowA, rowB, evalVal);
}

function evalEqualValues(rowA, rowB, evalVal) {
    if (rowA._data.name === rowB._data.name && rowA._data.category === rowB._data.category) {
        return numericOnly(rowA._data['preferred-order'].toString(10), rowB._data['preferred-order'].toString(10));
    }
    return evalVal;
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
