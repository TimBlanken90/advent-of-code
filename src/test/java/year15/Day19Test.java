package year15;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import common.Day;
import util.InputResolver;

public class Day19Test implements Day {

    @Override
    @Test
    public void partOne() throws IOException {
        var input = InputResolver.toList(15, 19);
        var start = "CRnCaCaCaSiRnBPTiMgArSiRnSiRnMgArSiRnCaFArTiTiBSiThFYCaFArCaCaSiThCaPBSiThSiThCaCaPTiRnPBSiThRnFArArCaCaSiThCaSiThSiRnMgArCaPTiBPRnFArSiThCaSiRnFArBCaSiRnCaPRnFArPMgYCaFArCaPTiTiTiBPBSiThCaPTiBPBSiRnFArBPBSiRnCaFArBPRnSiRnFArRnSiRnBFArCaFArCaCaCaSiThSiThCaCaPBPTiTiRnFArCaPTiBSiAlArPBCaCaCaCaCaSiRnMgArCaSiThFArThCaSiThCaSiRnCaFYCaSiRnFYFArFArCaSiRnFYFArCaSiRnBPMgArSiThPRnFArCaSiRnFArTiRnSiRnFYFArCaSiRnBFArCaSiRnTiMgArSiThCaSiThCaFArPRnFArSiRnFArTiTiTiTiBCaCaSiRnCaCaFYFArSiThCaPTiBPTiBCaSiThSiRnMgArCaF";

        var distinctMolecules = generateDistinctMolecules(start, input);
        assertEquals(535, distinctMolecules.size());
    }

    @Override
    @Test
    public void partTwo() throws IOException {

    }


    private Set<String> generateDistinctMolecules(String start, List<String> replacements) {
        var molecules = new HashSet<String>();
        for (var replacement : replacements) {
            var parts = replacement.split(" => ");
            var from = parts[0];
            var to = parts[1];
            generateMolecules(start, from, to, molecules);
        }
        return molecules;
    }

    private void generateMolecules(String molecule, String from, String to, Set<String> molecules) {
        int index = molecule.indexOf(from);
        while (index != -1) {
            var newMolecule = molecule.substring(0, index) + to + molecule.substring(index + from.length());
            molecules.add(newMolecule);
            index = molecule.indexOf(from, index + 1);
        }
    }


}
