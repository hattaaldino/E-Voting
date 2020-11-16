/*
 * Hi Hatta's here!! Enjoy my code!
 * Feel free to Approach me if you have anything to ask
 * mhattaldino@gmail.com
 */

package Response;

import java.io.Serializable;

/*
 * @author hattaldino
 */
public class ClientIdentity implements Serializable{
    private final String role;
    private int id;
    
    public ClientIdentity(String role, int id){
        this.role = role;
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
}
