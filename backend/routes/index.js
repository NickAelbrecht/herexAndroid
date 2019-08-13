

var express = require("express");
var router = express.Router();

let mongoose = require("mongoose");
let Recipe = mongoose.model("Recipe");



router.get("/recipes", function(req, res) {
  res.json({ recipes: "Bla bla recepten" });
});

/* GET home page. */
router.get("/", function(req, res, next) {
  res.json({test:"server works"});
});

router.listen(3000);
