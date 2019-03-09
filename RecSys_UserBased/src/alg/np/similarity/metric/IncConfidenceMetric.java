/**
 * Compute the similarity between two items based on increase in confidence
 */ 

package alg.np.similarity.metric;

import java.util.Map;

import profile.Profile;
import util.reader.DatasetReader;

public class IncConfidenceMetric implements SimilarityMetric
{
	private static double RATING_THRESHOLD = 4.0; // the threshold rating for liked items 
	private DatasetReader reader; // dataset reader
	
	/**
	 * constructor - creates a new IncConfidenceMetric object
	 * @param reader - dataset reader
	 */
	public IncConfidenceMetric(final DatasetReader reader)
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
		// initialize variables used for calculating the similarity
		double supp_x = 0, supp_nx = 0, supp_xy = 0, supp_nxy = 0;
		Map<Integer, Profile> user_profiles = reader.getUserProfiles(); // save user profiles to a variable for iteration in enhanced for loop
		
		// iterating through the keys/ids of the user profiles
		for(int user_id : user_profiles.keySet()) {
			// boolean used for if condition for rating of movie Y by a user
			boolean Y_bool = (user_profiles.get(user_id).contains(Y)) && (user_profiles.get(user_id).getValue(Y) >= RATING_THRESHOLD);
			if(user_profiles.get(user_id).contains(X)) {
				// if movie X rating is greater or equal to the rating threshold then execute contents of if statement
				if(user_profiles.get(user_id).getValue(X) >= RATING_THRESHOLD) {
					supp_x ++; // increment the counter related to the support(X)
					if(Y_bool) {
						supp_xy++; // if the Y_bool condition is true then increment the counter related to the supp(X and Y)
					}
				}
				else {
					// if rating of movie X is less than the threshold. Increment supp_nx counter
					supp_nx++;
					if(Y_bool) {
						supp_nxy++; // if the Y_bool condition is true then increment the counter related to supp(!X and Y )
					}
				}
			}
		}
		// calculate conf(X and Y) and conf(!X and Y) ensuring that division by zero does not occur by setting result to 0 if it is identified.
		double conf_xy = supp_x > 0.0 ? supp_xy / supp_x : 0.0;
		double conf_nxy = supp_nx > 0.0 ? supp_nxy / supp_nx : 0.0;	
		// return calculated similarity of the movies again ensuring that division by zero does not occur.
		return conf_nxy > 0.0 ? conf_xy / conf_nxy : 0.0;
	}
}
