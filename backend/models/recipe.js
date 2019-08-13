var mongoose = require("mongoose");

var RecipeSchema = new mongoose.Schema({
  title: String,
  products: [String],
  allergies: [String],
  kind: String,
});
mongoose.model("Recipe", RecipeSchema);