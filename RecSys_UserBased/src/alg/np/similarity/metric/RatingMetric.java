/**
 * Compute the similarity between two items based on the Cosine between item ratings
 */ 

package alg.np.similarity.metric;

import profile.Profile;
import util.reader.DatasetReader;

public class RatingMetric implements SimilarityMetric
{
	private DatasetReader reader; // dataset reader

	/**
	 * constructor - creates a new RatingMetric object
	 * @param reader - dataset reader
	 */
	public RatingMetric(final DatasetReader reader)
	{
		this.reader = reader;
	}

	/**
	 * computes the similarity between items
	 * @param X - the id of the first item 
	 * @param Y - the id of the second item
	 */
	public double getItemSimilarity(final Integer X, final Integer Y)
	{
		// initialize variables used for calculating cosine similarity
		double sumOfProduct = 0.0, norm_X = 0.0, norm_Y = 0.0; 
		
		// Save the ratings for movie X and Y into respective variables
		Profile X_ratings = reader.getItemProfiles().get(X);
		Profile Y_ratings = reader.getItemProfiles().get(Y);
		
		// for loop that iterates through the common ids of X_ratings and Y_ratings
		for(int user_id : X_ratings.getCommonIds(Y_ratings)) {
			// calculate the product of X and Y Genome scores and sum to the sumOfProduct
			sumOfProduct += X_ratings.getValue(user_id) * Y_ratings.getValue(user_id);
		}
		// getNorm() function will normalize the ratings vector for use in cosine similarity calculation
		norm_X = X_ratings.getNorm();
		norm_Y = Y_ratings.getNorm();

		// calculate the Cosine similarity. If the denominator is less than 0 then return 0 as similarity.
		return (norm_X * norm_Y) > 0 ? (sumOfProduct / (norm_X * norm_Y)) : 0;
	}
}
