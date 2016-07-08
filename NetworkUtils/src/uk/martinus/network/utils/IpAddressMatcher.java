package uk.martinus.network.utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpAddressMatcher {

    private static final long CONST_256_POW_3 = 16777216;
    private static final long CONST_256_POW_2 = 65536;
    private static final long CONST_256_POW_1 = 256;
    private static final int IPV6_PARTS = 8;
    private static final int IPV4_LENGTH = 32;
    private static final int IPV6_LENGTH = 128;
    private static final int RADIX_OCTAL = 8;
    private static final int RADIX_DECIMAL = 10;
    private static final int RADIX_HEX = 16;
    private static final BigInteger ALL_ONES = new BigInteger(
            "ffffffffffffffffffffffffffffffff", RADIX_HEX);
    private static final Pattern PATTERN_DECIMAL = Pattern
            .compile("[1-9][0-9]*.*");
    private static final Pattern PATTERN_HEX = Pattern
            .compile("0(x|X)[a-fA-F0-9]+");
    private static final Pattern PATTERN_OCTAL = Pattern.compile("0[0-9]*");
    private static final Pattern PATTERN_IPV4_MAPPED = Pattern
            .compile(".*(ffff|FFFF):(.*)");
    private static final Pattern PATTERN_MASK = Pattern.compile(".+\\/(\\d+)");
    private BigInteger prefixMask;
    private BigInteger baseValue;
    private boolean hasBaseAddressMask;
    private boolean ipv6;
    private IpType baseAddressType;

    /**
     * Matches two IP addresses, either or both can be IPv4 or IPv6. The base
     * address is specified in the call to this constructor. The address to
     * match is specified in the call to matches(). The base address can take
     * any legal format of IP address and supports the CIDR routing prefix
     * syntax to specify a range of addresses, e.g. 192.168.1.0/24 (specifies
     * the most significant 24 bits as the portion of the address to match
     * against). An IPv4 address will not match an IPv6 address unless it is an
     * IPv6-mapped IPv4 address; for example ::ffff:192.168.0.1 will match
     * against 192.168.0.1.
     * 
     * @param ipAddr
     *            The base address
     * @throws IllegalArgumentException
     */
    public IpAddressMatcher(String ipAddr) {
        String baseAddr = ipAddr;
        int prefix = 0;
        prefixMask = ALL_ONES;
        hasBaseAddressMask = false;
        Matcher maskMatcher = PATTERN_MASK.matcher(ipAddr);
        if (maskMatcher.matches()) {
            prefix = Integer.valueOf(maskMatcher.group(1)).intValue();
            baseAddr = ipAddr.substring(0, ipAddr.indexOf('/'));
            hasBaseAddressMask = true;
        }
        if (isIpv6OrIpv4Mapped(baseAddr)) {
            // Too early to set the type as IPv6, could be a mapped IPv4
            if (hasBaseAddressMask) {
                if (prefix > IPV6_LENGTH) {
                    throw new IllegalArgumentException(
                            "Routing prefix is too large");
                }
                prefixMask = ALL_ONES.shiftLeft(IPV6_LENGTH - prefix);
            }
            baseValue = parseIpv6(baseAddr);
        } else {
            // It's an IPv4
            baseAddressType = IpType.IPV4;
            if (hasBaseAddressMask) {
                if (prefix > IPV4_LENGTH) {
                    throw new IllegalArgumentException(
                            "Routing prefix is too large");
                }
                prefixMask = ALL_ONES.shiftLeft(IPV4_LENGTH - prefix);
            }
            baseValue = BigInteger.valueOf(parseIpv4(baseAddr));
        }
    }

    /**
     * Matches the IP address passed to this method against the IP address set
     * in the constructor.
     * 
     * @param ipAddr
     *            IP address to match
     * @return true if matches
     */
    public boolean matches(String ipAddr) {
        BigInteger ipValue;
        if (isIpv6OrIpv4Mapped(ipAddr)) {
            // This address is an IPv6, if the address originally passed to the
            // constructor is not IPv6 then don't match this
            if (ipv6 == false) {
                return false;
            }
            ipValue = parseIpv6(ipAddr);
        } else {
            // This address is an IPv4, if the address originally passed to the
            // constructor is not IPv4 then don't match this
            if (ipv6 == true) {
                return false;
            }
            ipValue = BigInteger.valueOf(parseIpv4(ipAddr));
        }
        BigInteger maskedBaseValue = baseValue.and(prefixMask);
        BigInteger maskedIpValue = ipValue.and(prefixMask);
        boolean result = maskedBaseValue.equals(maskedIpValue);
        return result;
    }

    private boolean isIpv6OrIpv4Mapped(String ipAddr) {
        if (ipAddr.contains(":")) {
            return true;
        }
        return false;
    }

    /**
     * Takes an IPv4 address and converts it to a long value.
     * 
     * @param ipAddr
     *            The IPv4 address
     * @return The long value
     * @throws IllegalArgumentException
     */
    private long parseIpv4(String ipAddr) {
        long value = 0;
        String[] ipAddrParts = ipAddr.split("\\.");
        switch (ipAddrParts.length) {
        case 1:
            // E.g. 3232235521 (<32-bits>)
            value = ipv4PartToLong(ipAddrParts[0]);
            break;
        case 2:
            // E.g. 192.11010049 (<8-bits>.<24-bits>)
            value = (ipv4PartToLong(ipAddrParts[0]) * CONST_256_POW_3)
                    + ipv4PartToLong(ipAddrParts[1]);
            break;
        case 3:
            // E.g. 192.168.1 (<8-bits>.<8-bits>.<16-bits>)
            value = (ipv4PartToLong(ipAddrParts[0]) * CONST_256_POW_3)
                    + (ipv4PartToLong(ipAddrParts[1]) * CONST_256_POW_2)
                    + ipv4PartToLong(ipAddrParts[2]);
            break;
        case 4:
            // E.g. 192.168.0.1 (<8-bits>.<8-bits>.<8-bits>.<8-bits>)
            value = (ipv4PartToLong(ipAddrParts[0]) * CONST_256_POW_3)
                    + (ipv4PartToLong(ipAddrParts[1]) * CONST_256_POW_2)
                    + (ipv4PartToLong(ipAddrParts[2]) * CONST_256_POW_1)
                    + ipv4PartToLong(ipAddrParts[3]);
            break;
        default:
            throw new IllegalArgumentException(
                    "Not a recognised IPv4 address format");
        }
        return value;
    }

    /**
     * Takes a part of an IPv4 address in either decimal, octal or hex format
     * and converts that part to a long value.
     * 
     * @param str
     *            IPv4 address part
     * @return The long value
     * @throws IllegalArgumentException
     */
    private long ipv4PartToLong(String str) {
        String outStr = str;
        int radix = RADIX_DECIMAL;
        Matcher patternDecimalMatcher = PATTERN_DECIMAL.matcher(str);
        Matcher patternHexMatcher = PATTERN_HEX.matcher(str);
        Matcher patternOctalMatcher = PATTERN_OCTAL.matcher(str);
        if (patternDecimalMatcher.matches()) {
            radix = RADIX_DECIMAL;
        } else if (patternHexMatcher.matches()) {
            radix = RADIX_HEX;
            outStr = str.substring(2, str.length());
        } else if (patternOctalMatcher.matches()) {
            radix = RADIX_OCTAL;
        } else {
            throw new IllegalArgumentException(
                    "Not a recognised IPv4 address format");
        }
        return Long.valueOf(outStr, radix).longValue();
    }

    /**
     * Takes an IPv6 address, expands any shortened forms and converts it to a
     * BigInteger value.
     * 
     * @param ipAddr
     *            The IPv6 address
     * @return The BigInteger value
     */
    private BigInteger parseIpv6(String ipAddr) {
        String addr = ipAddr;
        IpType ipAddrType;

        // Detect an IPv4-mapped IPv6 address
        Matcher m = PATTERN_IPV4_MAPPED.matcher(ipAddr);
        if (m.matches()) {
            ipAddrType = IpType.IPV4_MAPPED;
            // Group(2) matches the IPv4 portion
            return BigInteger.valueOf(parseIpv4(m.group(2)));
        }
        else {
            ipAddrType = IpType.IPV6;
        }

        // Expand any shortened forms fully
        if (ipAddr.startsWith(":")) {
            addr = ipAddr.substring(1, ipAddr.length());
        }
        if (ipAddr.endsWith(":")) {
            addr = ipAddr.substring(0, ipAddr.length() - 1);
        }
        String[] ipAddrParts = addr.split(":", IPV6_PARTS);
        int missing = IPV6_PARTS + 1 - ipAddrParts.length;
        List<String> expAddress = new ArrayList<>();
        for (String ipAddrPart : ipAddrParts) {
            if (ipAddrPart.equals("")) {
                for (int i = 0; i < missing; i++) {
                    expAddress.add("0");
                }
            } else {
                expAddress.add(ipAddrPart);
            }
        }
        // At this point expAddress contains an expanded IPv6 address

        // Now compute the binary value of the address
        BigInteger value = BigInteger.ZERO;
        // There are exactly 8 parts in the expanded address
        for (int i = IPV6_PARTS - 1; i >= 0; i--) {
            BigInteger bint = new BigInteger(expAddress.get(i), RADIX_HEX);
            value = value.add(bint.pow(i));
        }
        return value;
    }
    
    @Override
    public String toString() {
        return baseValue.toString(RADIX_HEX);
    }

    private static enum IpType {
        IPV4, //
        IPV4_MAPPED, //
        IPV6; //

        private IpType type(String ipAddr) {
            if (ipAddr.contains(":")) {
                // Either an IPv6 or IPv4-mapped
                Matcher m = PATTERN_IPV4_MAPPED.matcher(ipAddr);
                if (m.matches()) {
                    return IPV4_MAPPED;
                }
                else {
                    return IPV6;
                }
            }
            return IPV4;
        }
    }
}
