import { Box, Button, Flex, HStack, Heading, SimpleGrid, Spacer, Text, VStack } from '@chakra-ui/react'
import React from 'react'
import { DestinationCard } from '../components/DestinationCard'

const testData = [
    {
        location: "Paris",
        imageUrl: "https://picsum.photos/1100"
    },
    {
        location: "New York City",
        imageUrl: "https://picsum.photos/1200"
    },
    {
        location: "Ibadan",
        imageUrl: "https://picsum.photos/900"
    },
    {
        location: "Cooks Island",
        imageUrl: "https://picsum.photos/1000"
    }
]


export const Destinations = () => {
    return (
        <Flex flexDir={"column"} gap={4}>
            <HStack>
                <VStack alignItems={"start"} gap={0}>
                    <Heading fontSize={"xl"} color={"facebook.900"}>Oluwanifemi</Heading>
                    <Text color={"#aaa"} as={"i"} fontSize={"sm"}>It's a beautiful day in Paris</Text>
                </VStack>
                <Spacer />
                <Button size={"lg"}>
                    create destination
                </Button>
            </HStack>
            <Box roundedTop={"3xl"} h="30.5rem" p={4} bg={"#fff"}>
                <HStack mb={3}>
                    <Heading as={"h4"} fontSize={"lg"}>My Destinations</Heading>
                    <Spacer />
                    <Button variant={"link"} colorScheme='blue' size={"sm"}>view all</Button>
                </HStack>
                <SimpleGrid minChildWidth={"273px"} maxH={"27rem"} overflowY={"scroll"} spacing={"16px"}>
                    {testData.map((data, index) => {
                        return (<DestinationCard key={index} data={data} />)
                    })}
                </SimpleGrid>
            </Box>
        </Flex>
    )
}
