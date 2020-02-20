package com.romco.y2018qualification;

import com.romco.y2018qualification.input.Requirement;

public class Solution {
    private Requirement requirement;
    
    public Solution(Requirement requirement) {
        this.requirement = requirement;
    }
    
    /*
    1. go through all the rides and discard impossible ones (distance > (latest finish - earliest start) )
    2. create nv pools (nv = number of vehicles)
    3. sort the rides by -----earliest start asc, starting coordinates asc-----
    4. take the first ride, and try to find the best next ride, and keep going until there is no ride left which could be done.
        - ride is only considered if:
        a. the total completion time meets the latest finish requirement:
            end step of previous + distance to start + distance to end <= latest finish
        - the most optimal following ride is:
        a. the starting coordinates are as close as possible to the end of the previous ride
        b. the starting time is as close as possible to (but not earlier than): (end of previous + distance from end of previous to start of this)
      
    5. repeat 4 until all rides are done or out of vehicles
    6. end
    */
    
    
    
}
