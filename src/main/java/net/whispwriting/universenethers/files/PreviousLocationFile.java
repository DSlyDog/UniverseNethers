package net.whispwriting.universenethers.files;

import net.whispwriting.universenethers.UniverseNethers;

public class PreviousLocationFile extends AbstractFile {

    public PreviousLocationFile(UniverseNethers pl, String uuid){
        super (pl, uuid+".yml", "/playerdata/");
    }

}
