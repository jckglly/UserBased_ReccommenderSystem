/**
 * Compute the similarity between two items based on the Cosine between item genome scores
 */ 

package alg.np.similarity.metric;

import profile.Profile;
import util.reader.DatasetReader;

public class GenomeMetric implements SimilarityMetric
{
	private DatasetReader reader; // dataset reader
	
	/**
	 * constructor - creates a new GenomeMetric object
	 * @param reader - dataset reader
	 */
	public GenomeMetric(final DatasetReader reader)
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
		
		// Get genome score vectors for each movie 
		Profile X_item = reader.getItem(X).getGenomeScores();
		Profile Y_item = reader.getItem(Y).getGenomeScores();
		
		// for loop that iterates through the common ids of X_item and Y_item
		for(int id : X_item.getCommonIds(Y_item)) {
			// calculate the product of X and Y Genome scores and sum to the sumOfProduct
			sumOfProduct += (X_item.getValue(id) * Y_item.getValue(id)); 
		}
		
		// getNorm() function will normalize the genome score vector for each item
		norm_X = X_item.getNorm(); 	
		norm_Y = Y_item.getNorm(); 
		
		// calculate the Cosine similarity. If the denominator is less than 0 then return 0 as similarity. 
		return (norm_X * norm_Y) > 0 ? (sumOfProduct / (norm_X * norm_Y)) : 0;
	}
}
