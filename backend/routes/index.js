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
  let query = Recipe.find()
  query.exec(function(err, recepten) {
    if (err) {
      console.log(err, "+", req.query)

      return next(err);
    }
    let recept = recepten.filter(recept => recept.title = req.query.test)
    res.json(recept)
    });
});

router.post("/recipes", function(req, res, next) {
  console.log(req.body.title);
  let title = req.body.title
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
    res.json({title : recipe});
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

router.delete("/recipes", function(req, res, next){
  Recipe.remove({}, function(err, removed){
    if(err){
      return next(err)
    }
    res.json(removed)
  })
})


module.exports = router;
