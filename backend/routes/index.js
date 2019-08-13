var express = require("express");
var router = express.Router();

let mongoose = require("mongoose");
require("./../models/Recipe");

let Recipe = mongoose.model("Recipe");



/* GET home page. */
router.get("/", function(req, res, next) {
  res.json({ test: "server works" });
});

router.get("/recipes", function(req, res) {
  console.log("werkt")
  res.json({ recipes: "Bla bla recepten" });
});

router.post("/recipes", function(req, res, next) {
  console.log(req.body)
  let recipe = new Recipe({
    title: req.body.title,
    products: req.body.products,
    allergies: req.body.allergies,
    kind: req.body.kind
  });
  recipe.save(function(err) {
    if (err) {
      console.log("error!!!");
      return next(err);
    }
    res.json(recipe);
  });
});

module.exports = router;
