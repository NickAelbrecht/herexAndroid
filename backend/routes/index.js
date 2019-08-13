var express = require("express");
var router = express.Router();

let mongoose = require("mongoose");
require("./../models/Recipe");

let Recipe = mongoose.model("Recipe");



router.get("/recipes", function(req, res) {
  res.json({ recipes: "Bla bla recepten" });
});

/* GET home page. */
router.get("/", function(req, res, next) {
  res.json({test:"server works"});
});

router.post("/API/recipes/", function(req, res, next) {
  Recipe.create(function(err) {
    if (err) {
      return next(err);
    }
    let recipe = new Recipe({
      title: req.body.title,
      products: req.body.products,
      allergies: req.body.allergies,
      kind: req.body.kind
    });
    recipe.save(function(err, rec) {
      if (err) {
        console.log("error!!!");
        return next(err);
      }
      res.json(club);
    });
  });
});

