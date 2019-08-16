var express = require("express");
var router = express.Router();

let mongoose = require("mongoose");
require("./../models/Recipe");

let Recipe = mongoose.model("Recipe");

/* GET home page. */
router.get("/", function(req, res, next) {
  res.json({ test: "server works" });
});

//GET ALL RECIPES
router.get("/recipes", function(req, res) {
  let query = Recipe.find();
  query.exec(function(err, recipes) {
    if (err) {
      return next(err);
    }
    res.json(recipes);
  });
});

//GET RECIPES BY TITLE
router.get("/recipes/:title", function(req, res) {
  Recipe.find(function(err, recepten) {
    if (err) {
      return next(err);
    }
    let recept = recepten
      .find(rec => rec.title === req.params.title);
    res.json(recept);
  });
});

//ADD RECIPE
router.post("/recipes", function(req, res, next) {
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
    res.json({ recept: recipe });
  });
});

//Update recipe
router.put("/recipes", function(req, res, next) {
  let recipe = new Recipe({
    title: req.body.title,
    products: req.body.products,
    allergies: req.body.allergies,
    kind: req.body.kind
  });
  Recipe.remove({title: req.body.title},
    function(err, recipe) {
      if (err) {
        return next(err);
      }
  })
  recipe.save(function(err) {
    if (err) {
      console.log("error!!!");
      return next(err);
    }
    res.json({ recept: recipe });
  });
})


//DELETE RECIPE BY TITLE
router.delete("/recipes/:title", function(req, res, next) {
  Recipe.remove(
    {
      title: req.params.title
    },
    function(err, recipe) {
      if (err) {
        return next(err);
      }
      res.json(recipe);
    }
  );
});

//DELETE ALL RECIPES
router.delete("/recipes", function(req, res, next) {
  Recipe.remove({}, function(err, removed) {
    if (err) {
      return next(err);
    }
    res.json(removed);
  });
});



module.exports = router;
