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
  let query = Recipe.find();
  query.exec(function(err, recipes) {
    if (err) {
      return next(err);
    }
    res.json(recipes);
  });
});

router.get("/recipes/:title", function(req, res) {
  console.log(req.query)
  let query = Recipe.find({title : req.query.title})
  query.exec(function(err, recept) {
    if (err) {
      console.log(err, "+", req.query)

      return next(err);
    }
    res.json(recept);
  });
});

router.post("/recipes", function(req, res, next) {
  console.log(req.body);
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

router.delete("/recipes/:title", function(req, res, next){
  Recipe.deleteOne({
    title: req.params.tile
  }, function(err, recipe){
    if(err){
      return next(err);
    }
    res.json("Deleted")
  })
})


module.exports = router;
